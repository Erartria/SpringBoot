package com.leonid.springboot.services;

import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repo;
    @PersistenceContext
    private EntityManager em;

    ProfileServiceImpl(ProfileRepository profileRepository) {
        this.repo = profileRepository;
    }

    @Override
    public void add(Profile profile) {
        repo.saveAndFlush(profile);
    }

    @Override
    public List<Profile> getAll() {
        return repo.findAll();
    }

    @Override
    public Profile getById(int id) {
        Optional<Profile> profile = repo.findById(id);
        return profile.isPresent() ? profile.get() : null;
    }

    @Override
    public boolean update(Profile profile, int id) {
        if (repo.existsById(id)) {
            profile.setId(id);
            repo.save(profile);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
