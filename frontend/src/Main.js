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


const Main = () => {
  return (
    <Switch> {/* The Switch decides which component to show based on the current URL.*/}
        <Route exact path='/dashboard' component={Dashboard}></Route>
        <ProtectedRoute exact path='/bikeproduction' component={BikeProduction}></ProtectedRoute>
        <ProtectedRoute exact path='/inventory' component={Inventory}></ProtectedRoute>
        <ProtectedRoute exact path='/infrastructure' component={Infrastructure}></ProtectedRoute>
        <ProtectedRoute exact path='/manufacturing' component={Manufacturing}></ProtectedRoute>
        <ProtectedRoute exact path='/accounting' component={Accounting}></ProtectedRoute>
        <ProtectedRoute exact path='/createuser' component={CreateUser}></ProtectedRoute>
        <Route exact path='/login' component={Login}></Route>
    </Switch>
  );
}

export default Main;