#  Smart Office Application &  Design Patterns Demos (Java)

This repository contains my submission for the coding challenge.  
It is divided into **two parts**:

- **Exercise 1:** Six Java demos implementing different design patterns.  
- **Exercise 2:** Smart Office Booking System (console-based Java application).

All code follows best practices:
- Java 11+  
- Each class in its own file  
- Organized packages  
- Logging, validations, exception handling  
- No infinite `while(true)` loops (graceful shutdown)  
- Defensive programming  

---

---

##  Exercise 1 – Design Pattern Demos

I implemented six unique, real-world scenarios to demonstrate depth of understanding:

| Pattern (Type)      | Scenario / Demo                          |
|---------------------|------------------------------------------|
| **Observer** (Behavioral)  | Stock Market Ticker updates MobileApp & WebDashboard |
| **Command** (Behavioral)   | Smart Home Remote executes Light/Thermostat commands |
| **Singleton** (Creational) | Central Logger Service              |
| **Factory Method** (Creational) | Transport Factory creates Car/Bike/Bus          |
| **Adapter** (Structural)   | Payment Gateway Adapter (Stripe via PayPal interface) |
| **Composite** (Structural) | University Hierarchy (Departments contain Professors/Students) |

Each demo has a `main()` and prints sample output to illustrate the pattern in action.

---

##  Exercise 2 – Smart Office Booking System

A **console-based Java application** to manage office rooms and bookings.

###  Features

- **Room Booking:** Book rooms for specific time and duration.  
- **Occupancy Management:** Add/remove occupants with capacity checks.  
- **Auto Release:** Scheduled release of unused rooms.  
- **Status Reports:** Clean, color-coded status reports sorted chronologically.  
- **CSV Export:** Adapter pattern to export bookings in CSV format.  
- **Graceful Exit:** Long-running app until user types `exit`.

###  Design Patterns Used Inside Smart Office

- **Singleton:** `OfficeManager` (central controller).  
- **Factory:** `RoomFactory` (create different room types).  
- **Observer:** `LightController` & `ACController` (auto actions on occupancy).  
- **Command:** Each CLI action (book, cancel, status) implemented as `Command`.  
- **Adapter:** `CSVBookingExporter` (convert bookings to CSV).  

---

##  OOP Concepts Used

This project heavily demonstrates core Object-Oriented Programming concepts:

- **Encapsulation:**  
  - All data members are private; accessed via public getters/setters.  
  - Room capacity, booking details, and services encapsulated within their classes.

- **Abstraction:**  
  - `Command` interface abstracts all CLI commands.  
  - `BookingExporter` interface abstracts export behavior from concrete CSV implementation.  

- **Inheritance:**  
  - `Room` base class could be extended for different room types in future.  
  - Observers implement a common interface to be plugged into any Room.

- **Polymorphism:**  
  - `execute()` method in each `Command` subclass behaves differently but called uniformly.  
  - `BookingExporter` interface allows multiple exporter types (CSV, JSON, etc.) without changing client code.

- **Composition:**  
  - `OfficeManager` composes `BookingService` and manages `Room` objects.  
  - `Department` in Composite pattern composes multiple `Member` objects.

- **Interfaces & Loose Coupling:**  
  - Commands, Exporters, and Observers all use interfaces → easy to extend and test.

- **Defensive Programming & Validations:**  
  - Validating room IDs, capacities, booking durations before use.  
  - Gracefully handling invalid input or exceptions.

---

##  How to Build & Run (Windows PowerShell)

### Compile Smart Office app

```powershell
cd smart-office
mkdir out -Force
javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out org.example.smartoffice.SmartOfficeApp


## 📂 Repository Structure

