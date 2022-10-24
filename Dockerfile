FROM mysql

# set root password 123456
ENV MYSQL_ROOT_PASSWORD 123456

# copy and run sql script blog.sql to create schema blog and corresponding tables
ADD blog.sql /docker-entrypoint-initdb.d

EXPOSE 3306