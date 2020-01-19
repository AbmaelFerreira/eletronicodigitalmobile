package com.eletronicodigitalmobile.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eletronicodigitalmobile.domain.Estado;

@Repository
public interface EstadoRepository  extends JpaRepository<Estado, Integer>{

}
