package by.artsem.footballrestapi.services;

import by.artsem.footballrestapi.models.Club;
import by.artsem.footballrestapi.repository.ClubRepository;
import by.artsem.footballrestapi.util.DataNotFoundedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClubService {
    private final ClubRepository clubRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Transactional
    public void saveClub(Club club) {
        clubRepository.save(club);
    }

    @Transactional
    public void removeClub(Club club) {
        clubRepository.delete(club);
    }

    public List<Club> getClubs() {
        return clubRepository.findAll();
    }

    public Club findById(Long id) {
        return clubRepository.findById(id).orElseThrow(DataNotFoundedException::new);
    }

    public Club findByName(String name) {
        return clubRepository.findByName(name).orElseThrow(DataNotFoundedException::new);
    }
}
