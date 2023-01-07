package actions;

import actions.changepage.ChangePageLogOut;
import actions.changepage.ChangePageLogin;
import actions.changepage.ChangePageMovies;
import actions.changepage.ChangePageRegister;
import actions.changepage.ChangePageSeeDetails;
import actions.changepage.ChangePageUpgrades;
import actions.database.AddDataBase;
import actions.database.DeleteDataBase;
import actions.onpage.Back;
import actions.onpage.BuyPremiumAccount;
import actions.onpage.BuyTokens;
import actions.onpage.Filter;
import actions.onpage.Like;
import actions.onpage.Login;
import actions.onpage.Purchase;
import actions.onpage.RateMovie;
import actions.onpage.Register;
import actions.onpage.Search;
import actions.onpage.Subscribe;
import actions.onpage.Watch;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import lombok.Getter;
import lombok.Setter;

public abstract class Action {
    @Getter
    @Setter
    private String type, page;

    /**
     * Factory method for creating
     * @param actionInput data stored in ActionInput format -> it should be translated
     * @return Corresponding Action object
     */
    public static Action createAction(final ActionInput actionInput) {

        if (actionInput.getType().equals("back"))
            return new Back(actionInput);

        if (actionInput.getType().equals("database")) {
            switch (actionInput.getFeature()) {
                case "add":
                    return new AddDataBase(actionInput);
                case "delete":
                    return new DeleteDataBase(actionInput);
            }
        }

        if (actionInput.getType().equals("change page")) {
            return switch (actionInput.getPage()) {
                case "login" -> new ChangePageLogin(actionInput);
                case "register" -> new ChangePageRegister(actionInput);
                case "logout" -> new ChangePageLogOut(actionInput);
                case "movies" -> new ChangePageMovies(actionInput);
                case "see details" -> new ChangePageSeeDetails(actionInput);
                case "upgrades" -> new ChangePageUpgrades(actionInput);
                default -> null;
            };
        }

        return switch (actionInput.getFeature()) {
            case "register" -> new Register(actionInput);
            case "login" -> new Login(actionInput);
            case "search" -> new Search(actionInput);
            case "filter" -> new Filter(actionInput);
            case "buy tokens" -> new BuyTokens(actionInput);
            case "buy premium account" -> new BuyPremiumAccount(actionInput);
            case "purchase" -> new Purchase(actionInput);
            case "watch" -> new Watch(actionInput);
            case "like" -> new Like(actionInput);
            case "rate" -> new RateMovie(actionInput);
            case "subscribe" -> new Subscribe(actionInput);
            default -> null;
        };
    }

    /**
     * Common method for any derived class
     * @return objectNode for printing purposes
     */
    public abstract ObjectNode apply();

    public Action(final ActionInput actionInput) {
        this.type = actionInput.getType();
        this.page = actionInput.getPage();
    }
}
