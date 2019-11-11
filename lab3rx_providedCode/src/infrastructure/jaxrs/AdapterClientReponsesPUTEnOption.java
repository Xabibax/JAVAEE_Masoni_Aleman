package infrastructure.jaxrs;

import infrastructure.langage.Types;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

@Priority(Priorities.HEADER_DECORATOR + 2)
public class AdapterClientReponsesPUTEnOption implements ReaderInterceptor {

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext reponse) throws IOException, WebApplicationException {
        if (reponse.getType().equals(Optional.class)) {
            ParameterizedType t = ((ParameterizedType) reponse.getGenericType());
            Type[] args = t.getActualTypeArguments();
            Class<?> classe = Types.convertirTypeEnClasse(args[0]);
            reponse.setType(classe);
            // reponse.setType(
            // 	Types.convertirTypeEnClasse(
            // 		((ParameterizedType) reponse.getGenericType())
            // 			.getActualTypeArguments()[0]
            // 	)
            // );
            System.out.println("------------ OK");
            return Optional.of(reponse.proceed());
        } else {
            System.out.println("------------ PAS OK");
            return reponse.proceed();
        }
    }

}
