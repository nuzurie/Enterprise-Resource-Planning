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


const Main = () => {
  return (
    <Switch> {/* The Switch decides which component to show based on the current URL.*/}
        <Route exact path='/dashboard' component={Dashboard}></Route>
        <Route exact path='/bikeproduction' component={BikeProduction}></Route>
        <Route exact path='/inventory' component={Inventory}></Route>
        <Route exact path='/infrastructure' component={Infrastructure}></Route>
        <Route exact path='/manufacturing' component={Manufacturing}></Route>
        <Route exact path='/accounting' component={Accounting}></Route>
        <Route exact path='/createuser' component={CreateUser}></Route>
        <Route exact path='/login' component={Login}></Route>

    </Switch>
  );
}

export default Main;