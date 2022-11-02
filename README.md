# Blog

## About

This blog project is built with spring boot and react. Here, it's the back-end part.

## Get Started

1. Run the front-end application

   Follow the instructions in [front-end part](https://github.com/DanyueZhang/react-spring-boot-blog-front-end)

2. Build database on docker

   ```
   # cd into root directory of project
   
   # build mysql image
   $ docker build -t blogmysql:0.1 .
   
   # build redis image
   $ docker pull redis
   
   # create and run mysql and redis containers
   $ docker-compose -f docker-compose.yaml up -d
   ```
   
3. Change local storage address

   cd into `react-spring-boot-blog-back-end/src/main/java/com/danyue/reactspringbootblogbackend/constants/SystemConstants.java`
   and change the `STORAGE_IMAGE_URL` to your own address

4. run the back-end application

   (default admin name and password is `admin` and `123456`)