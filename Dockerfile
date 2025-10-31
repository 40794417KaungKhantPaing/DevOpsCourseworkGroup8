# Use the latest OpenJDK image as the base
FROM openjdk:24

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build context into the container
COPY ./target/gp8CourseWork.jar /app/

# Define the command to run the app
ENTRYPOINT ["java", "-jar", "gp8CourseWork.jar", "db:3306", "30000"]
