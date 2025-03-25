# Multi-Level Queue Scheduling Algorithm

---

## 📘 Introduction
**Multi-Level Queue Scheduling** is an OS scheduling algorithm that assigns processes to different queues based on their priority. This project demonstrates the implementation of Multi-Level Queue Scheduling using two different queue types: **Round Robin (RR)** for time-sliced preemptive scheduling, and **Shortest Job First (SJF)** for non-preemptive scheduling. The project includes process scheduling, context switching, and calculating key metrics like turnaround time, waiting time, and response time.

---

## 🧩 Problem Statement
Operating systems often need to manage multiple processes efficiently. With different types of processes having varying priorities, Multi-Level Queue Scheduling provides a solution for managing these processes by categorizing them into different queues based on priority and burst time. However, the challenge lies in implementing an efficient scheduler that can handle preemptive and non-preemptive scheduling techniques like RR and SJF.

---

## 💡 Solution
This project demonstrates the **Multi-Level Queue** scheduler with the following characteristics:
- **Queue 1**: Implements **Round Robin (RR)** scheduling with a time quantum of 3ms.
- **Queue 2**: Implements **Shortest Job First (SJF)**, where the process with the shortest burst time is executed first.
- The algorithm handles both **preemptive** and **non-preemptive** scheduling based on the queue priority and process arrival times.

The **RR** queue preempts processes after the time quantum expires, while **SJF** processes are handled without preemption, ensuring the shortest burst time processes are executed first.

---


## 💻 Technology Stack
- **Programming Language**: Java
- **Key Concepts**: Round Robin (RR), Shortest Job First (SJF), Multi-Level Queue Scheduling

---

## 🚀 Launching Instructions

1. **📥 Clone the Repository**
   - Open the Command Prompt or Terminal.
   - Clone the repository by running:

     ```bash
     git clone https://github.com/yourusername/Multi-Level-Queue-Scheduler.git
     ```

2. **🔧 Install Prerequisites**
   - Ensure you have **Java** installed on your machine.
   - You can download and install Java from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

3. **▶️ Run the Application**
   - Compile and run the Java application:

     ```bash
     javac Driver.java
     java Driver
     ```

