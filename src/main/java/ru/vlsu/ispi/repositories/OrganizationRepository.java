package ru.vlsu.ispi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vlsu.ispi.beans.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
