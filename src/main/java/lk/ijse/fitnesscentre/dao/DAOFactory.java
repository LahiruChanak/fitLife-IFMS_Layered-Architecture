package lk.ijse.fitnesscentre.dao;

import lk.ijse.fitnesscentre.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDAOFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        ATTENDANCE, MEMBER, MEMBERSHIP, PAYMENT, PRODUCT, PURCHASE, PURCHASE_DETAILS, SCHEDULE, SCHEDULE_DETAILS, TRAINER, TRAINER_DETAILS, CREDENTIAL, QUERY_DAO
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

            case PRODUCT:
                return new ProductDAOImpl();

            case PURCHASE:
                return new PurchaseDAOImpl();

            case PURCHASE_DETAILS:
                return new PurchaseDetailDAOImpl();

            case QUERY_DAO:
                return new QueryDAOImpl();

            case SCHEDULE:
                return new ScheduleDAOImpl();

            case SCHEDULE_DETAILS:
                return new ScheduleDetailsDAOImpl();

            case TRAINER:
                return new TrainerDAOImpl();

            case TRAINER_DETAILS:
                return new TrainerDetailsDAOImpl();

            case CREDENTIAL:
                return new CredentialDAOImpl();

            default:
                return null;
        }
    }

}
