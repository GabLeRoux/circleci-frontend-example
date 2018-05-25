'use strict';

var configSource = process.env.NODE_ENV || 'development';
module.exports = require('./env/' + configSource);