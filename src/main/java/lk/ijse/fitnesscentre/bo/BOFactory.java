package lk.ijse.fitnesscentre.bo;

import lk.ijse.fitnesscentre.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getBOFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        ATTENDANCE, CREDENTIAL, MEMBER, MEMBERSHIP, PAYMENT, PLACE_ORDER, PRODUCT, PURCHASE, PURCHASE_DETAILS, PURCHASE_HISTORY, SCHEDULE, SCHEDULE_DETAILS, TRAINER, TRAINER_DETAILS
    }


    //Object Creation Logic

    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case ATTENDANCE:
                return new AttendanceBOImpl();

            case CREDENTIAL:
                return new CredentialBOImpl();

            case MEMBER:
                return new MemberBOImpl();

            case MEMBERSHIP:
                return new MembershipBOImpl();

            case PAYMENT:
                return new PaymentBOImpl();

            case PRODUCT:
                return new ProductBOImpl();

            case PURCHASE:
                return new PurchaseBOImpl();

            case PURCHASE_DETAILS:
                return new PurchaseDetailBOImpl();

            case PURCHASE_HISTORY:
                return new PurchaseHistoryBOImpl();

            case SCHEDULE:
                return new ScheduleBOImpl();

            case SCHEDULE_DETAILS:
                return new ScheduleDetailsBOImpl();

            case TRAINER:
                return new TrainerBOImpl();

            case TRAINER_DETAILS:
                return new TrainerDetailsBOImpl();

            case PLACE_ORDER:
                return new PlaceOrderBOImpl();

            default:
                return null;
        }
    }

}
