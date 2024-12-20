package coms309.Cycino.GameSettings.BlackJack;

import coms309.Cycino.Games.Blackjack.BlackJack;
import coms309.Cycino.login.LoginInfo;
import coms309.Cycino.login.LoginRepository;
import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import coms309.Cycino.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlackJackSettingsService {
    @Autowired
    private BlackJackSettingsRepository blackJackSettingsRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginRepository repo;

    public BlackJackSettings getBlackJackSettings(Long id) {
        LoginInfo login = repo.findById(id).orElse(null);
        assert login != null;
        User u = login.getUser();
        assert u != null;
        return u.getBlackJackSettings();
    }

    @Transactional
    public Boolean updateBlackJackSettings(BlackJackSettings settings, Long id) {
        boolean ok = false;
        User user = userService.getUser(id);
        BlackJackSettings userSettings = user.getBlackJackSettings();
        if(userSettings.getUserId().equals(id)) {
            userSettings.setDealerStandOn(settings.getDealerStandOn());
            userSettings.setAmountOfDecks(settings.getAmountOfDecks());
            userSettings.setMaxBet(settings.getMaxBet());
            userSettings.setMinBet(settings.getMinBet());
            ok = true;
        }
        return ok;
    }
}
