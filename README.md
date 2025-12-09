Task Tracker CLI app for roadmap.sh

Commands:
  add (desc)
  delete (id)
  update (id, newDesc)
  mark (id, newStatus)
  exit

Install:
  git clone https://github.com/broodguy/CLI-Task-Tracker
  cd CLI-Task-Tracker
  mvn clean package
  java -jar target/CLI-Task-Tracker-1.0-SNAPSHOT-jar-with-dependencies.jar
