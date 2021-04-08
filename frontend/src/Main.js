import React from 'react';
import { Switch, Route } from 'react-router-dom';

import Dashboard from './pages/Dashboard.js';
import BikeProduction from './pages/BikeProduction.js';
import Inventory from './pages/Inventory.js';
import Infrastructure from './pages/Infrastructure.js';
import Manufacturing from './pages/Manufacturing.js';
import Accounting from './pages/Accounting.js';
import CreateUser from './pages/CreateUser.js';
import Login from './pages/Login.js';
import ProtectedRoute from "./ProtectedRoute";
import EmployeeRoute from "./EmployeeRoute";

const Main = () => {
  return (
    <Switch> {/* The Switch decides which component to show based on the current URL.*/}
        <EmployeeRoute exact path='/dashboard' component={Dashboard}></EmployeeRoute>
        <EmployeeRoute exact path='/bikeproduction' component={BikeProduction}></EmployeeRoute>
        <EmployeeRoute exact path='/inventory' component={Inventory}></EmployeeRoute>
        <EmployeeRoute exact path='/infrastructure' component={Infrastructure}></EmployeeRoute>
        <EmployeeRoute exact path='/manufacturing' component={Manufacturing}></EmployeeRoute>
        <ProtectedRoute exact path='/accounting' component={Accounting}></ProtectedRoute>
        <ProtectedRoute exact path='/createuser' component={CreateUser}></ProtectedRoute>
        <Route exact path='/login' component={Login}></Route>
    </Switch>
  );
}

export default Main;