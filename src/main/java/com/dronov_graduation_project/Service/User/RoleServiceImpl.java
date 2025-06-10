package com.dronov_graduation_project.Service.User;

import com.dronov_graduation_project.Model.User.Role;
import com.dronov_graduation_project.Repository.User.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с ролями
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Role> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Role> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Role create(Role role) {
        return repository.save(role);
    }

    @Override
    public Role update(Role role) {
        return repository.save(role);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findByName(name);
    }
}
