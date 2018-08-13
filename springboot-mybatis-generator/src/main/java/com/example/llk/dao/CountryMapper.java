package com.example.llk.dao;

import com.example.llk.entity.Country;
import com.example.llk.entity.CountryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountryMapper {
    int countByExample(CountryExample example);

    int deleteByExample(CountryExample example);

    int deleteByPrimaryKey(String code);

    int insert(Country record);

    int insertSelective(Country record);

    List<Country> selectByExample(CountryExample example);

    Country selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") Country record, @Param("example") CountryExample example);

    int updateByExample(@Param("record") Country record, @Param("example") CountryExample example);

    int updateByPrimaryKeySelective(Country record);

    int updateByPrimaryKey(Country record);
}