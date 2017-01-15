import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

function tick() {
    const element = (
        <div>
            <h1>Hello now</h1>
            <h2>it is {new Date().toLocaleTimeString()}</h2>
        </div>
    );
    ReactDOM.render(
        element,
        document.getElementById('ticktock')
    );
}

setInterval(tick, 1000);

const element2 = React.createElement(
    'h1',
    {className: 'greeting'},
    'hello world!'
);
ReactDOM.render(
    element2,
  document.getElementById('root')
);

function Welcome(props) {
    return <h1>Hello, {props.name}</h1>
}

const welcomeElement = <Welcome name="Sara"/>;
ReactDOM.render(
    welcomeElement,
    document.getElementById('welcome')
);