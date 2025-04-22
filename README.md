# Blockchain Voting System

A secure, transparent voting system built using Java AWT, implementing a blockchain to ensure vote integrity and prevent tampering.

## Features
- **Secure Voting**: Each vote is recorded as a block in the blockchain with a unique hash.
- **Voter Authentication**: Validates voter ID and name before allowing a vote.
- **Candidate Selection**: Choose from a predefined list of candidates and their parties.
- **Results Display**: View election results with vote counts and winner announcement.
- **User Interface**: Simple GUI built with Java AWT for voting and viewing results.

## Prerequisites
- Java Development Kit (JDK) 8 or higher

## How to Run
1. Compile the Java file:
   ```bash
   javac BlockchainVotingSystem.java
   ```
2. Run the application:
   ```bash
   java BlockchainVotingSystem
   ```

## Usage
1. Enter a valid Voter ID and Name (predefined in the code).
2. Select a candidate from the dropdown menu.
3. Click "Submit Vote" to record the vote on the blockchain.
4. Click "End Elections and View Results" to see the final tally and winner.
5. Use the "Back to Voting" button to return to the voting interface.

## Sample Voters
- ID: V001, Name: Aman Bhardwaj
- ID: V002, Name: Jeet Bhardwaj
- ID: V003, Name: Sumit Dhariwal
- ID: 1, Name: 1

## Sample Candidates
- Narendra Modi (BJP)
- Yogi Aditynath (BJP)
- Arvind Kejriwal (AAP)
- Rahul Gandhi (INC)

## Blockchain Structure
- Each block contains:
  - Previous block's hash
  - Vote data
  - Timestamp
  - Unique hash (SHA-256)
  - Voter ID and selected candidate

## Notes
- The system prevents duplicate voting by checking voter IDs.
- Results are calculated only after ending the election.
- The blockchain ensures vote immutability and transparency.
