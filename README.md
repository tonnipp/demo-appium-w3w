# Mobile Automation Testing Assignment

## Overview

This project contains automated mobile test scenarios for the what3words Android application using:

* Java
* Maven
* Appium
* TestNG
* Page Object Model (POM)
* Explicit Wait Strategy

The automation suite focuses on validating critical user journeys around the Search feature and ensuring core business functionality remains stable across releases.

---

# Environment Setup

## Prerequisites

Before running the project, ensure the following software is installed:

### 1. Java Development Kit (JDK)

Recommended version:

* JDK 17+

Verify installation:

```bash
java -version
```

---

### 2. Android Studio

Download and install Android Studio.

Required components:

* Android SDK
* Android SDK Platform Tools
* Android Emulator
* Android SDK Build Tools

Verify ADB installation:

```bash
adb devices
```

---

### 3. Create Android Virtual Device (AVD)

Open Android Studio:

```text
Tools → Device Manager → Create Device
```

Recommended configuration:

* Device: Pixel 8
* Android Version: Android 16 (API Level 36)
* System Image: Google Play Image

Using a Google Play system image is required because the test environment installs the production version of the what3words application directly from the Google Play Store instead of using a locally provided APK.

After creating the emulator, start the device and complete the initial Android setup:

1. Sign in with a Google account.
2. Open Google Play Store.
3. Search for **what3words**.
4. Install the latest production version of the application.
5. Launch the application once to complete initialization.

Verify the emulator is available:

```bash
adb devices
```

Expected output:

```bash
List of devices attached
emulator-5554 device
```

Verify the application is installed:

```bash
adb shell pm list packages | grep what3words
```

Expected output:

```bash
com.what3words.android
```


---

### 4. Install Appium

Verify Node.js:

```bash
node -v
npm -v
```

Install Appium:

```bash
npm install -g appium
```

Verify:

```bash
appium -v
```

---

### 5. Install Appium Doctor

```bash
npm install -g appium-doctor
```

Run diagnostics:

```bash
appium-doctor --android
```

Resolve all reported issues before executing tests.

---

# Pull Source Code

Clone repository:

```bash
git clone <repository-url>
```

Navigate to project:

```bash
cd project-name
```

---

# Install Dependencies

Download Maven dependencies:

```bash
mvn clean install
```

---

# Execute Test Suite

Run all tests:

```bash
mvn test
```

Run specific suite:

```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

# Test Coverage

The automation suite covers the most business-critical search scenarios in what3words.

## 1. Verify user can search valid locations

### Objective

Ensure users can successfully search valid addresses or what3words locations and receive accurate results.

### Business Impact

Search is the primary entry point to the what3words ecosystem.

If valid searches fail:

* Users cannot discover locations.
* Navigation workflows become unusable.
* Location sharing becomes impossible.
* Customer trust is significantly impacted.

### Production Risk

A defect in this area directly affects:

* User acquisition
* User retention
* Core navigation usage
* Partner integrations relying on location lookup

---

## 2. Verify shared address matches selected search result

### Objective

Validate that the shared what3words address exactly matches the address selected by the user.

### Business Impact

Location sharing is one of the core business functions of what3words.

Users commonly share locations for:

* Deliveries
* Emergency services
* Meetups
* Logistics operations

### Production Risk

If incorrect addresses are shared:

* Users may arrive at wrong destinations.
* Delivery failures may occur.
* Emergency response delays may occur.
* User confidence in the platform decreases.

This scenario protects the integrity of location communication across platforms.

---

## 3. Verify invalid searches are handled correctly

### Objective

Verify the application responds gracefully when users enter invalid, malformed, or non-existent locations.

### Business Impact

Real-world users frequently enter:

* Typographical errors
* Incomplete addresses
* Random text
* Unsupported formats

The application must provide meaningful feedback rather than crashing or becoming unresponsive.

### Production Risk

Without proper handling:

* Increased application crashes
* Poor user experience
* Higher abandonment rates
* Increased support tickets

This test ensures application stability under negative user inputs.

---

## 4. Verify user can recover from invalid search and continue searching

### Objective

Ensure users can continue using search after an invalid search attempt.

### Business Impact

Users rarely enter perfect search queries on the first attempt.

A resilient search experience allows users to quickly correct mistakes and continue their journey.

### Production Risk

If recovery is broken:

* Users may become trapped in an error state.
* Search sessions may terminate unexpectedly.
* Conversion and engagement rates may decrease.

This test validates application resilience and recovery behavior.

---

## 5. shouldSearchMultipleLocationsSequentially

### Objective

Verify users can perform multiple consecutive searches within a single session.

### Business Impact

Real users frequently compare multiple locations before making a decision.

Typical examples:

* Route planning
* Delivery planning
* Travel preparation
* Meeting point comparison

### Production Risk

Potential issues include:

* State management failures
* Cached result corruption
* Memory leaks
* UI synchronization issues
* Session instability

This test helps identify defects that only appear after repeated user interactions and long-running sessions.

---

# Task 2 – Share Feature Test Design

The Share feature is a critical workflow that enables users to distribute precise what3words addresses through external communication channels.

The test design includes:

* Functional scenarios
* End-to-End scenarios
* Cross-platform scenarios
* Error handling scenarios
* Permission handling scenarios
* Deep-link validation scenarios

Test cases link: [atm-exporter.xlsx](https://github.com/user-attachments/files/29343060/atm-exporter.xlsx)
Can check it in Jira Board also in Zephyrs


### Jira and Confluence Reference

Test cases are maintained in Jira:

```text
https://nguyenluonghoangvu1999.atlassian.net/?continue=https%3A%2F%2Fnguyenluonghoangvu1999.atlassian.net%2Fwelcome%2Fsoftware%3FprojectId%3D10003&atlOrigin=eyJpIjoiOTAwOTMwYTUwMTc0NGNiNmEwOWFlODU1ZmE4MTM2MmQiLCJwIjoiamlyYS1zb2Z0d2FyZSJ9
```

```text
[https://nguyenluonghoangvu1999.atlassian.net/jira/software/c/projects/WT/summary?atlOrigin=eyJpIjoiODAyMDRlYWQ5NDFlNDM2MzhiMWNmZjVmOGNjNTJmOTMiLCJwIjoiaiJ9](https://nguyenluonghoangvu1999.atlassian.net/wiki/spaces/TE/pages/983041/Search+Feature+Test+Scenarios?atlOrigin=eyJpIjoiYmFiYWNkZTcwZTBlNDljZThmOTdiZjY0MzRhMzE4YjIiLCJwIjoiaiJ9)
```
```text
https://nguyenluonghoangvu1999.atlassian.net/wiki/spaces/TE/pages/426167/BUSINESS+RESEARCH+DOCUMENT+RESEARCH+BRD?atlOrigin=eyJpIjoiNDk3YzlhNWNhNzU0NDU1MWI3ZjJkYjE3MzJmYWE1YmQiLCJwIjoiaiJ9
```
```text
https://nguyenluonghoangvu1999.atlassian.net/wiki/spaces/TE/pages/426148/Technical+Test+Mobile+Automation+Engineer?atlOrigin=eyJpIjoiNzA1OTg5N2U2ZWY5NDA2M2FlNGI0NzkwYjIyMjBjZmEiLCJwIjoiaiJ9
```
```text
https://nguyenluonghoangvu1999.atlassian.net/wiki/spaces/TE/pages/1015814/Share+Feature+Test+Scenarios?atlOrigin=eyJpIjoiMjljNWIwMzBjMmNkNDFkZDkwMTdkMzJhNGE5ZDNjZGYiLCJwIjoiaiJ9
```

The Jira project contains:

* Share Feature Test Cases
* Edge Cases
* Cross-platform Validation Scenarios
* End-to-End Share Flows
* Traceability to Requirements
* Defect Linking Support
* Execution Reports

```
```
