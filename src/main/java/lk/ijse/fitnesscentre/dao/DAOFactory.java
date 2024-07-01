package lk.ijse.fitnesscentre.dao;

import lk.ijse.fitnesscentre.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDAOFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        ATTENDANCE, MEMBER, MEMBERSHIP, PAYMENT, PLACE_ORDER, PRODUCT, PURCHASE, PURCHASE_DETAILS, PURCHASE_HISTORY, SCHEDULE, SCHEDULE_DETAILS, TRAINER, TRAINER_DETAILS
    }


    //Object Creation Logic

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case ATTENDANCE:
                return new AttendanceDAOImpl();

            case MEMBER:
                return new MemberDAOImpl();

            case MEMBERSHIP:
                return new MembershipDAOImpl();

            case PAYMENT:
                return new PaymentDAOImpl();

//            case PLACE_ORDER:
//                return new PlaceOrderDAOImpl();

            case PRODUCT:
                return new ProductDAOImpl();

            case PURCHASE:
                return new PurchaseDAOImpl();

            case PURCHASE_DETAILS:
                return new PurchaseDetailDAOImpl();

            case PURCHASE_HISTORY:
                return new PurchaseHistoryDAOImpl();

            case SCHEDULE:
                return new ScheduleDAOImpl();

            case SCHEDULE_DETAILS:
                return new ScheduleDetailsDAOImpl();

            case TRAINER:
                return new TrainerDAOImpl();

            case TRAINER_DETAILS:
                return new TrainerDetailsDAOImpl();

            default:
                return null;
        }
    }

}
