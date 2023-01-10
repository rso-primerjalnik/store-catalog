package si.fri.rso.storecatalog.graphql;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.storecatalog.lib.Store;
import si.fri.rso.storecatalog.services.beans.StoreBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
@CrossOrigin(allowOrigin = "*")
public class StoreQueries {

    @Inject
    private StoreBean storeBean;

    @GraphQLMutation
    public Store addStore(@GraphQLArgument(name = "store") Store store) {
        return storeBean.createStore(store);
    }

    @GraphQLMutation
    public Store editStore(@GraphQLArgument(name = "storeId") Integer id,
                           @GraphQLArgument(name = "store") Store store) {
        return storeBean.putStore(id, store);
    }

    @GraphQLMutation
    public DeleteResponse deleteStore(@GraphQLArgument(name = "storeId") Integer id) {
        return new DeleteResponse(storeBean.deleteStore(id));
    }

    @GraphQLQuery
    public PaginationWrapper<Store> getAllStores (@GraphQLArgument(name = "pagination") Pagination pagination,
                                               @GraphQLArgument(name = "sort") Sort sort,
                                               @GraphQLArgument(name = "filter") Filter filter) {

        return GraphQLUtils.process(storeBean.getStores(), pagination, sort, filter);
    }

    @GraphQLQuery
    public Store getSingleStore(@GraphQLArgument(name = "id") Integer id) {
        return storeBean.getStoreById(id);
    }

}
