package ru.vlsu.ispi.logic.abstractions;

import org.springframework.ui.Model;
import ru.vlsu.ispi.beans.Sportsman;

import java.sql.SQLException;

public interface ISportsmanHandler {
    public void HandleIndex(Long id, Long playerId, Model model);

    public void SportsmanFormOpen(Model model) throws SQLException;

    public void SportsmanFormSubmit(Sportsman sportsman, Model model) throws SQLException;
}
