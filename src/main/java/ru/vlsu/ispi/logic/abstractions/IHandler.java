package ru.vlsu.ispi.logic.abstractions;

import org.springframework.ui.Model;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;

import java.sql.SQLException;

public interface IHandler {
    public void HandleIndex(Long id, Long playerId, Model model);
    public void ClubFormOpen(Long id, Model model) throws SQLException;
    public void ClubFormSubmit(Club club, Model model) throws SQLException;
    public void DeleteClub(Long id, Model model) throws SQLException;
    public void SportsmanFormOpen(Model model) throws SQLException;
    public void SportsmanFormSubmit(Sportsman sportsman, Model model) throws SQLException;
}
