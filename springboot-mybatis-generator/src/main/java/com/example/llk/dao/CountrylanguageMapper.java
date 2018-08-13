package com.example.llk.dao;

import com.example.llk.entity.Countrylanguage;
import com.example.llk.entity.CountrylanguageExample;
import com.example.llk.entity.CountrylanguageKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountrylanguageMapper {
    int countByExample(CountrylanguageExample example);

    int deleteByExample(CountrylanguageExample example);

    int deleteByPrimaryKey(CountrylanguageKey key);

    int insert(Countrylanguage record);

    int insertSelective(Countrylanguage record);

    List<Countrylanguage> selectByExample(CountrylanguageExample example);

    Countrylanguage selectByPrimaryKey(CountrylanguageKey key);

    int updateByExampleSelective(@Param("record") Countrylanguage record, @Param("example") CountrylanguageExample example);

    int updateByExample(@Param("record") Countrylanguage record, @Param("example") CountrylanguageExample example);

    int updateByPrimaryKeySelective(Countrylanguage record);

    int updateByPrimaryKey(Countrylanguage record);
}