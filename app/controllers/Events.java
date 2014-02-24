package controllers;

import models.Alert;
import models.ExternalEvent;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.DateUtils;
import views.html.error;

import java.util.List;

public class Events extends Controller {

  public static Result filterEvents() {
    DynamicForm dynamicForm = DynamicForm.form().bindFromRequest();
    String selectedTimeUnit = dynamicForm.get("pastTimeSelect");

    Long adjustedTimestamp = utils.DateUtils.getMillis() - DateUtils.TimeUnit.valueOf(selectedTimeUnit).getMilliseconds();
    flash("pastTimeSelect", selectedTimeUnit);
    return redirect(routes.Events.eventsByPage(0, adjustedTimestamp));
  }

  public static Result eventsByPage(Integer page, Long afterTimestamp) {
    Integer pages;
    final Integer ROWS_PER_PAGE = 10;
    Long after = (afterTimestamp == null) ? 0 : afterTimestamp;
    List<Alert> alerts = Alert.find().where()
        .eq("device.person.email", session("email"))
        .gt("timestamp", after)
        .order("timestamp desc")
        .findPagingList(ROWS_PER_PAGE)
        .getPage(page)
        .getList();

    pages = Alert.find().where().eq("device.person.email", session("email")).gt("timestamp", after).findRowCount() / ROWS_PER_PAGE;

    return ok(views.html.privatemonitoring.privatealerts.render(alerts, page, pages));
  }

  @Security.Authenticated(Secured.class)
  public static Result eventDetails(Long alertId) {
    Alert alert = Alert.find().where().eq("primaryKey", alertId).findUnique();
    ExternalEvent externalEvent = alert.getExternalEvent();
    Form<ExternalEvent> externalEventForm;

    if(externalEvent == null) {
      externalEventForm = Form.form(ExternalEvent.class);
    }
    else {
      externalEventForm = Form.form(ExternalEvent.class).fill(externalEvent);
    }

    return ok(views.html.privatemonitoring.alertdetails.render(alert, externalEventForm));
  }

  @Security.Authenticated(Secured.class)
  public static Result updateEventDetails(Long alertId) {
    Alert alert = Alert.find().where().eq("primaryKey", alertId).findUnique();
    Form<ExternalEvent> externalEventForm = Form.form(ExternalEvent.class).bindFromRequest();

    if (externalEventForm.hasErrors()) {
      return ok(error.render("Problem updating alert", externalEventForm.errors().toString()));
    }

    ExternalEvent externalEvent = externalEventForm.get();
    externalEvent.getAlerts().add(alert);
    alert.setExternalEvent(externalEvent);
    externalEvent.save();
    alert.save();

    flash("updated", "External Event Updated");

    return redirect(routes.Events.eventDetails(alertId));
  }
}
