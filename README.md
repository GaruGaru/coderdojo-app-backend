# Coderdojo App Backend

[![Build Status](https://travis-ci.org/GaruGaru/coderdojo-app-backend.svg?branch=master)](https://travis-ci.org/GaruGaru/coderdojo-app-backend)
[![Docker Pulls](https://img.shields.io/docker/pulls/garugaru/coderdojo-app-backend.svg)](https://hub.docker.com/r/garugaru/coderdojo-app-backend/)

### Fast, parallel, container ready aggregator for coderdojo events

## Supported platforms 

- Eventbrite 
- Zen *[in-progress]*
 
 
# Run locally 

    docker run -p 80:80 -e EVENT_BRITE_TOKEN=A_VALID_TOKEN garugaru/coderdojo-app-backend

# Powered with ♥ by

- [Kotlin](https://kotlinlang.org)
- [Javalin - Simple REST APIs](https://javalin.io/)
- [RxJava2 - Reactive Extensions for the JVM](https://github.com/ReactiveX/RxJava)
- [Retrofit - Type-safe HTTP client](http://square.github.io/retrofit/)