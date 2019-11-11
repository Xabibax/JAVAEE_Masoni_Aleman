package infrastructure.jaxrs;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

import infrastructure.langage.Types;
import modele.Livre;

@Priority(Priorities.HEADER_DECORATOR + 2)
public class AdapterClientReponsesPUTEnOption implements ReaderInterceptor {

	@Override
	public Object aroundReadFrom(ReaderInterceptorContext reponse) throws IOException, WebApplicationException {
		if (reponse.getType().equals(Optional.class)) {
			ParameterizedType t = ((ParameterizedType) reponse.getGenericType());
			Type[] args =  t.getActualTypeArguments();
			Class<?> classe = Types.convertirTypeEnClasse(args[0]);
			reponse.setType(classe);
			System.out.println("------------ OK");
			return Optional.of(reponse.proceed());
		} else {
			System.out.println("------------ PAS OK");
			return reponse.proceed();
		}
	}

}
