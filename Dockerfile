FROM node:8

WORKDIR /usr/src/app

# install GNU parallel for CI build
RUN apt-get update \
  && apt-get install -y --no-install-recommends \
  parallel \
  && rm -rf /var/lib/apt/lists/*

COPY package*.json ./
RUN npm install
COPY bower.json ./
RUN npm run bower -- install --allow-root
COPY . .

CMD [ "npm", "start" ]