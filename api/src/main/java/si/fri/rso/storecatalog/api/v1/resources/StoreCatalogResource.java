package si.fri.rso.storecatalog.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.storecatalog.lib.Store;
import si.fri.rso.storecatalog.services.beans.StoreBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@Log
@ApplicationScoped
@Path("/stores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, OPTIONS, PUT")
public class StoreCatalogResource {

    private Logger log = Logger.getLogger(StoreCatalogResource.class.getName());

    @Inject
    private StoreBean storeBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all stores.", summary = "Get all stores")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of stores.",
                    content = @Content(schema = @Schema(implementation = Store.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getStores() {

        List<Store> stores = storeBean.getStores();

        return Response.status(Response.Status.OK).entity(stores).build();
    }

    @Operation(description = "Get filtered stores.", summary = "Get filtered stores")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "List of filtered stores",
                    content = @Content(schema = @Schema(implementation = Store.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of stores in list")}
            )
    })
    @GET
    @Path("/filter")
    public Response getFilteredProducts() {
        List<Store> stores = storeBean.getFilteredStores(uriInfo);
        return Response.ok(stores).header("X-Total-Count", stores.size()).build();
    }

    @Operation(description = "Get store by id.", summary = "Get store by id")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Store information.",
                    content = @Content(
                            schema = @Schema(implementation = Store.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Store not found."
            )})
    @GET
    @Path("/{storeId}")
    public Response getStoreById(@Parameter(description = "Store ID.", required = true)
                                        @PathParam("storeId") Integer storeId) {

        Store store = storeBean.getStoreById(storeId);

        if (store == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(store).build();
    }

    @Operation(description = "Add new store.", summary = "Add new store")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Store successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error. Missing parameters.")
    })
    @POST
    public Response createStore(@RequestBody(
            description = "DTO object with store data.",
            required = true, content = @Content(
            schema = @Schema(implementation = Store.class))) Store store) {

        if (store.getName() == null || store.getUrl() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            store = storeBean.createStore(store);
        }

        return Response.status(Response.Status.CREATED).entity(store).build();
    }


    @Operation(description = "Update single store data.", summary = "Update store")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Store successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Store not found."
            )
    })
    @PUT
    @Path("/{storeId}")
    public Response putStore(@Parameter(description = "Store ID.", required = true)
                                    @PathParam("storeId") Integer storeId,
                                    @RequestBody(
                                            description = "DTO object with store data.",
                                            required = true, content = @Content(
                                            schema = @Schema(implementation = Store.class)))
                                            Store store) {

        store = storeBean.putStore(storeId, store);

        if (store == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(store).build();
    }


    @Operation(description = "Delete store.", summary = "Delete store")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Store successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Store not found."
            )
    })
    @DELETE
    @Path("/{storeId}")
    public Response deleteStore(@Parameter(description = "Store ID.", required = true)
                                       @PathParam("storeId") Integer storeId) {

        boolean deleted = storeBean.deleteStore(storeId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
