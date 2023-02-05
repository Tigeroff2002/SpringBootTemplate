package ru.vlsu.ispi.logic.abstractions;

import org.springframework.ui.Model;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;

public interface IHandler {
    public void HandleIndex(Long id, Long playerId, Model model);
    public void ClubFormOpen(Long id, Model model);
    public void ClubFormSubmit(Club club, Model model);
    public void DeleteClub(Long id, Model model);
    public void SportsmanFormOpen(Model model);
    public void SportsmanFormSubmit(Sportsman sportsman, Model model);
}
