package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.ScheduleDetailsBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.QueryDAO;
import lk.ijse.fitnesscentre.dao.custom.ScheduleDetailsDAO;
import lk.ijse.fitnesscentre.dao.custom.impl.QueryDAOImpl;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.dto.ScheduleDetailsDTO;
import lk.ijse.fitnesscentre.entity.ScheduleDetails;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDetailsBOImpl implements ScheduleDetailsBO {

    ScheduleDetailsDAO scheduleDetailsDAO = (ScheduleDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SCHEDULE_DETAILS);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY_DAO);

    @Override
    public List<ScheduleDetailsDTO> getAllScheduleDetails() throws SQLException {

        List<ScheduleDetailsDTO> allScheduleDetails = new ArrayList<>();
        List<ScheduleDetails> all = queryDAO.getScheduleDetails();

        for (ScheduleDetails scheduleDetails : all) {
            allScheduleDetails.add(new ScheduleDetailsDTO(scheduleDetails.getScheduleId(),scheduleDetails.getMemberId()));
        }
        return allScheduleDetails;
    }

    @Override
    public boolean addScheduleDetails(ScheduleDetailsDTO entity) throws SQLException {
        return scheduleDetailsDAO.add(new ScheduleDetails(entity.getScheduleId(),entity.getMemberId()));
    }

    @Override
    public boolean updateScheduleDetails(ScheduleDetailsDTO entity) throws SQLException {
        return scheduleDetailsDAO.update(new ScheduleDetails(entity.getScheduleId(),entity.getMemberId()));
    }

    @Override
    public boolean deleteScheduleDetails(String scheduleId,String memberId) throws SQLException {
        return scheduleDetailsDAO.delete(scheduleId,memberId);
    }

}
