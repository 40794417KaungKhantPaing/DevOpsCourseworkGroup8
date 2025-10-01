# Use the latest OpenJDK image as the base
FROM openjdk:24

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build context into the container
COPY ./target/DevOpsGroup8CourseWork-1.0-SNAPSHOT-jar-with-dependencies.jar /app/

# Define the command to run the app
ENTRYPOINT ["java", "-jar", "DevOpsGroup8CourseWork-1.0-SNAPSHOT-jar-with-dependencies.jar"]
