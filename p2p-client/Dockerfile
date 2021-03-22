FROM node:15.12.0-alpine3.10

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

ENV PATH /app/node_modules/.bin:$PATH

COPY . ./

EXPOSE 3000

CMD ["npm", "start"]