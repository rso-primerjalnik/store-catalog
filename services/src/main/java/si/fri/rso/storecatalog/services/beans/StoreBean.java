package si.fri.rso.storecatalog.services.beans;

import si.fri.rso.storecatalog.lib.Store;
import si.fri.rso.storecatalog.models.converters.StoreConverter;
import si.fri.rso.storecatalog.models.entities.StoreEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class StoreBean {

    private Logger log = Logger.getLogger(StoreBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Store> getStores() {
        TypedQuery<StoreEntity> query = em.createNamedQuery(
                "StoreEntity.getAll", StoreEntity.class);

        List<StoreEntity> resultList = query.getResultList();

        return resultList.stream().map(StoreConverter::toDto).collect(Collectors.toList());
    }

    public Store getStoreById(Integer id) {

        StoreEntity storeEntity = em.find(StoreEntity.class, id);

        if (storeEntity == null) {
            throw new NotFoundException();
        }

        Store store = StoreConverter.toDto(storeEntity);

        return store;
    }

    public Store createStore(Store store) {

        StoreEntity storeEntity = StoreConverter.toEntity(store);

        try {
            beginTx();
            em.persist(storeEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (storeEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return StoreConverter.toDto(storeEntity);
    }

    public Store putStore(Integer id, Store store) {

        StoreEntity c = em.find(StoreEntity.class, id);

        if (c == null) {
            return null;
        }

        StoreEntity updatedStoreEntity = StoreConverter.toEntity(store);

        try {
            beginTx();
            updatedStoreEntity.setId(c.getId());
            updatedStoreEntity = em.merge(updatedStoreEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return StoreConverter.toDto(updatedStoreEntity);
    }

    public boolean deleteStore(Integer id) {

        StoreEntity store = em.find(StoreEntity.class, id);

        if (store != null) {
            try {
                beginTx();
                em.remove(store);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
