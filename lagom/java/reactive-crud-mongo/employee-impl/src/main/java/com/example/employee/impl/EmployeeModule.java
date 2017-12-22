/*
 *
 */
package com.example.employee.impl;

import com.example.employee.api.EmployeeService;
import com.google.inject.AbstractModule;

import com.google.inject.Provides;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.ServerSettings;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;

import com.typesafe.config.*;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * The module that binds the EmployeeService so that it can be served.
 */
public class EmployeeModule extends AbstractModule implements ServiceGuiceSupport {

  @Override
  protected void configure() {

      bindService(EmployeeService.class, EmployeeServiceImpl.class);
      bind(SimpleCrudTemplate.class).to(EmployeeTemplate.class);
  }

  Config conf = ConfigFactory.load();
  String mongoHost = conf.getString("mongo.host");

    @Provides
    @Singleton
    public MongoCollection mongoCollection(){

        ConnectionPoolSettings connectionPoolSettings = ConnectionPoolSettings.builder()
                .maxSize(200)
                .maxConnectionIdleTime(1000, TimeUnit.MILLISECONDS)
                .build();

        ServerSettings serverSettings = ServerSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://" + mongoHost))
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .serverSettings(serverSettings)
                .connectionPoolSettings(connectionPoolSettings).build();

        return MongoClients.create(settings)
                .getDatabase("salaries")
                .getCollection("employee_chicago_salaries");
    }
}
