package com.epf.Service;
import com.epf.Persistance.Map;
import java.util.List;
import java.util.Optional;

public interface Service_MapDAO {
     Map create(Map map);
     Map update(Map map);
     void delete(Map map);
     Optional<Map> findById(int id);
     List<Map> findAll();
}


