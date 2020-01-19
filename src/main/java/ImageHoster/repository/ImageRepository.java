package ImageHoster.repository;

import ImageHoster.model.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;


@Repository
public class ImageRepository {


    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;



    public Image uploadImage(Image newImage) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newImage);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return newImage;
    }

    public List<Image> getAllImages() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Image> query = em.createQuery("SELECT i from Image i", Image.class);
        List<Image> resultList = query.getResultList();

        return resultList;
    }

    public Image getImageByTitle(String title) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Image> typedQuery = em.createQuery("SELECT i from Image i where i.title =:title", Image.class).setParameter("title", title);
            return typedQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public Image getImage(Integer imageId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Image> typedQuery = em.createQuery("SELECT i from Image i where i.id =:imageId", Image.class).setParameter("imageId", imageId);
        Image image = typedQuery.getSingleResult();
        return image;
    }

    public void updateImage(Image updatedImage) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(updatedImage);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }


    public void deleteImage(Integer imageId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Image image = em.find(Image.class, imageId);
            em.remove(image);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

}