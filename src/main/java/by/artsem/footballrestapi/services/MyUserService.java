package by.artsem.footballrestapi.services;

import by.artsem.footballrestapi.exceptions.DataNotFoundedException;
import by.artsem.footballrestapi.models.MyUser;
import by.artsem.footballrestapi.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MyUserService {
    private final MyUserRepository myUserRepository;

    @Autowired
    public MyUserService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Transactional
    public void saveUser(MyUser user) {
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        user.setCreatedWho("ADMIN");//TODO
        myUserRepository.save(user);
    }

    @Transactional
    public void removeUser(MyUser user) {
        myUserRepository.delete(user);
    }

    public List<MyUser> findUsers()    {
        return myUserRepository.findAll();
    }

    public MyUser findById(Long id) {
        return myUserRepository.findById(id).orElseThrow(() -> new DataNotFoundedException("User mot founded"));
    }

    @Transactional
    public MyUser findByName(String name) {
        return myUserRepository.findByUsername(name).orElseThrow(() -> new DataNotFoundedException("User mot founded"));
    }

}
