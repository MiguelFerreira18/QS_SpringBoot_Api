package com.example.test.demo.service;


import com.example.test.demo.model.Wish;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class WishListService {
    private static final String COL_NAME = "wishList";
    private static final String COL_NAME_DOCENTE ="docente";

    /**
     * Metodo para criar uma wish na base de dados
     * @param wish wish a receber para ser criada
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createWish(Wish wish) throws ExecutionException, InterruptedException
    {
        if(checkAll(wish))
        {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future2 = db.collection(COL_NAME_DOCENTE).whereEqualTo("docenteNumber",wish.getIdDocente()).get();
        List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
        if(documents2.isEmpty())
        {
            return null;
        }

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Wish oldWish = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldWish = doc.toObject(Wish.class);
            if (oldWish.getIdWish() > biggest) {
                biggest = oldWish.getIdWish();

            }
        }
        wish.setIdWish(biggest + 1);
        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().create(wish);
        return colApiFuture.get().getUpdateTime().toString();

    }

    /**
     * Metodo para eliminar uma wish da base de dados
     * @param idWish identificacao da wish a eliminar
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteWish(int idWish) throws ExecutionException, InterruptedException
    {
        if(idWish < 0)
        {
            return null;
        }

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot>  future= db.collection(COL_NAME).whereEqualTo("idWish",idWish).get();
        if (future.get().size()<=0)
            return null;

        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return "wish deleted with:"+idWish;
    }

    /**
     * Metodo para retornar uma lista de wish
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Wish> getAllWishes() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream().map((document) -> document.toObject(Wish.class)).toList();
    }

    /**
     * Metodo para atualizar uma wish da base de dados
     * @param wish wish a receber como parametro para atualizar uma wish existente na base de dados
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateWish(Wish wish) throws ExecutionException, InterruptedException {
        if(checkAll(wish))
        {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future2 = db.collection(COL_NAME_DOCENTE).whereEqualTo("docenteNumber",wish.getIdDocente()).get();
        List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
        if(documents2.isEmpty())
        {
            return null;
        }

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("idWish", wish.getIdWish()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if(documents.isEmpty())
            return null;

        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(wish);
        return "wish updated with:"+wish.getIdWish();

    }

    //Metodos auxiliares

    /**
     * Metodo para auxiliar na criacao e atualizao da wish
     * @param wish Wish a receber como parametro para ser verificada
     * @return
     */
    public boolean checkAll(Wish wish)
    {
        if(wish.getDescricaoMaterial().equalsIgnoreCase("")
                || wish.getDescricaoMaterial() == null
                || wish.getDescricaoMaterial().length() > 252
                || wish.getDescricaoMaterial().length() < 16 )
        {
            return true;
        }else if(wish.getIdDocente() < 0 )
        {
            return true;
        }else if(wish.getNomeMaterial().equalsIgnoreCase("")
                || wish.getNomeMaterial() == null
                || wish.getNomeMaterial().length() < 8
                || wish.getNomeMaterial().length() > 32 )
        {
            return true;
        }else if(wish.getIdWish() < 0)
        {
            return true;
        } else if(wish.getDate() == null)
        {
            return true;
        }
        //A DATA N FOI AVALIADO PQ DEPENDE DA INTERFACE DO ANDROID STUDIO; VALIDAR NO FIM DO PROJETO
        return false;
    }

}
