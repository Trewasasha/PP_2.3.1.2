package web.DAO;

import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOImp implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }
    @Override
    public User getUserById(int id) {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }
    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }
    @Override
    public void updateUser(int id, User updatedUser) {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.id = :id", User.class
        );
        query.setParameter("id", id);
        User user = query.getSingleResult();
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        user.setAge(updatedUser.getAge());
        entityManager.merge(user);
    }
    @Override
    public void deleteUser(int id) {
        entityManager.remove(getUserById(id));
    }
}
