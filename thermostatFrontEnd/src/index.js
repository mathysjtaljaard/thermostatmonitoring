import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import './index.css';
import ThermostatData from "./ThermoStatData";
import Visualization from "./DataVisualization";

ReactDOM.render(
  <App />,
  document.getElementById('root')
);

ReactDOM.render(
    <ThermostatData/>,
    document.getElementById('data')
);

ReactDOM.render(
    <Visualization/>,
    document.getElementById('visual')
);