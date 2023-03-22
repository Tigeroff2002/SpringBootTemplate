package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.ispi.repositories.OrganizationRepository;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;
}
