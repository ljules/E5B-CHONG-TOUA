package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Armure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArmureDao : JpaRepository<Armure, Long> {

}