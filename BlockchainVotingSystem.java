import java.awt.*;
import java.awt.event.*;
import java.security.*;
import java.util.*;
import java.util.List;

public class BlockchainVotingSystem extends Frame {

    private List<Block> blockchain = new ArrayList<>();
    private List<Voter> voters = new ArrayList<>();
    private List<Candidate> candidates = new ArrayList<>();
    
    private Panel mainPanel, votingPanel, resultsPanel;
    private CardLayout cardLayout = new CardLayout();
    private Button submitVoteBtn, viewResultsBtn, backToVoteBtn;
    private Label titleLabel, candidateLabel, voterIdLabel, voterNameLabel, statusLabel;
    private TextField voterIdField, voterNameField;
    private Choice candidateChoice;
    private TextArea resultsArea;
    
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color ACCENT_COLOR = new Color(231, 76, 60);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private final Color TEXT_COLOR = new Color(44, 62, 80);
    
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    private boolean electionsEnded = false;

    public BlockchainVotingSystem() {
        super("Blockchain-based Secure Voting System");
        initializeComponents();
        setupUI();
        setupBlockchain();
        addEventListeners();
    }
    
    private void initializeComponents() {
        candidates.add(new Candidate("Narendra Modi", "BJP"));
        candidates.add(new Candidate("Yogi Aditynath", "BJP"));
        candidates.add(new Candidate("Arvind Kejriwal", "AAP"));
        candidates.add(new Candidate("Rahul Gandhi", "INC"));
        
        voters.add(new Voter("1", "1"));
        voters.add(new Voter("V001", "Aman Bhardwaj"));
        voters.add(new Voter("V002", "Jeet Bhardwaj"));
        voters.add(new Voter("V003", "Sumit Dhariwal"));
        
        Block genesisBlock = new Block("0", "Genesis Block");
        blockchain.add(genesisBlock);
    }
    
    private void setupUI() {
        setSize(1920,1024); 
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        mainPanel = new Panel(cardLayout);
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        votingPanel = createVotingPanel();
        mainPanel.add(votingPanel, "VOTE");

        resultsPanel = createResultsPanel();
        mainPanel.add(resultsPanel, "RESULTS");
        
        add(mainPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        
        setLocationRelativeTo(null);
    }
    
    private Panel createVotingPanel() {
        Panel panel = new Panel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        
        titleLabel = new Label("Secure Blockchain Voting System", Label.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        voterIdLabel = new Label("Voter ID:");
        voterIdLabel.setFont(LABEL_FONT);
        voterIdLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(voterIdLabel, gbc);
        
        voterIdField = new TextField(30);
        voterIdField.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        panel.add(voterIdField, gbc);

        voterNameLabel = new Label("Voter Name:");
        voterNameLabel.setFont(LABEL_FONT);
        voterNameLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(voterNameLabel, gbc);
        
        voterNameField = new TextField(30);
        voterNameField.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        panel.add(voterNameField, gbc);

        candidateLabel = new Label("Select Candidate:");
        candidateLabel.setFont(LABEL_FONT);
        candidateLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(candidateLabel, gbc);
        
        candidateChoice = new Choice();
        candidateChoice.setFont(TEXT_FONT);
        for (Candidate candidate : candidates) {
            candidateChoice.add(candidate.getName() + " (" + candidate.getParty() + ")");
        }
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        panel.add(candidateChoice, gbc);

        submitVoteBtn = new Button("Submit Vote");
        styleButton(submitVoteBtn);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        panel.add(submitVoteBtn, gbc);

        viewResultsBtn = new Button("End Elections and View Results");
        styleButton(viewResultsBtn, ACCENT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(viewResultsBtn, gbc);

        statusLabel = new Label("", Label.CENTER);
        statusLabel.setFont(LABEL_FONT);
        statusLabel.setForeground(ACCENT_COLOR);
        statusLabel.setPreferredSize(new Dimension(600, 40));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);
        
        return panel;
    }
    
    private Panel createResultsPanel() {
        Panel panel = new Panel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        
        Label resultsTitle = new Label("Election Results", Label.CENTER);
        resultsTitle.setFont(TITLE_FONT);
        resultsTitle.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        panel.add(resultsTitle, gbc);

        resultsArea = new TextArea(20, 60);
        resultsArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultsArea.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        panel.add(resultsArea, gbc);

        backToVoteBtn = new Button("Back to Voting");
        styleButton(backToVoteBtn);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0;
        panel.add(backToVoteBtn, gbc);
        
        return panel;
    }
    
    private void styleButton(Button button) {
        styleButton(button, SECONDARY_COLOR);
    }
    
    private void styleButton(Button button, Color bgColor) {
        button.setFont(BUTTON_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(250, 50));
    }
    
    private void setupBlockchain() {
        if (blockchain.isEmpty()) {
            Block genesisBlock = new Block("0", "Genesis Block");
            blockchain.add(genesisBlock);
        }
    }
    
    private void addEventListeners() {
        submitVoteBtn.addActionListener(e -> submitVote());
        viewResultsBtn.addActionListener(e -> endElectionsAndViewResults());
        backToVoteBtn.addActionListener(e -> cardLayout.show(mainPanel, "VOTE"));
    }
    
    private void submitVote() {
        String voterId = voterIdField.getText().trim();
        String voterName = voterNameField.getText().trim();
        int selectedIndex = candidateChoice.getSelectedIndex();
        
        if (voterId.isEmpty() || voterName.isEmpty()) {
            statusLabel.setText("Please enter both Voter ID and Name");
            return;
        }
        
        if (selectedIndex == -1) {
            statusLabel.setText("Please select a candidate");
            return;
        }
        
        for (Block block : blockchain) {
            if (block.getVoterId() != null && block.getVoterId().equals(voterId)) {
                statusLabel.setText("This voter has already cast a vote");
                return;
            }
        }
        
        boolean voterValid = false;
        for (Voter voter : voters) {
            if (voter.getId().equals(voterId) && voter.getName().equalsIgnoreCase(voterName)) {
                voterValid = true;
                break;
            }
        }
        
        if (!voterValid) {
            statusLabel.setText("Invalid voter credentials");
            return;
        }
        
        Candidate selectedCandidate = candidates.get(selectedIndex);
        String voteData = voterId + " voted for " + selectedCandidate.getName();
        Block lastBlock = blockchain.get(blockchain.size() - 1);
        Block newBlock = new Block(lastBlock.getHash(), voteData);
        newBlock.setVoterId(voterId);
        newBlock.setCandidate(selectedCandidate.getName());
        blockchain.add(newBlock);

        voterIdField.setText("");
        voterNameField.setText("");
        
        statusLabel.setText("Vote recorded successfully! Transaction Hash: " + newBlock.getHash());
    }
    
    private void endElectionsAndViewResults() {
        electionsEnded = true;
        calculateResults();
        cardLayout.show(mainPanel, "RESULTS");
    }
    
    private void calculateResults() {
        if (blockchain.size() <= 1) {
            resultsArea.setText("No votes have been cast yet.");
            return;
        }
        
        Map<String, Integer> voteCount = new HashMap<>();
        for (Candidate candidate : candidates) {
            voteCount.put(candidate.getName(), 0);
        }
        
        for (int i = 1; i < blockchain.size(); i++) {
            Block block = blockchain.get(i);
            if (block.getCandidate() != null) {
                voteCount.put(block.getCandidate(), voteCount.get(block.getCandidate()) + 1);
            }
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== ELECTION RESULTS ===\n\n");
        sb.append("Total Votes Cast: ").append(blockchain.size() - 1).append("\n\n");
        
        sb.append("Candidate\t     Party\t  Votes\n");
        sb.append("---------------------------------------------------\n");
        
        for (Candidate candidate : candidates) {
            sb.append(String.format("%-20s%-20s%d\n", 
                candidate.getName(), 
                candidate.getParty(), 
                voteCount.get(candidate.getName())));
        }

        String winner = "";
        int maxVotes = -1;
        for (Map.Entry<String, Integer> entry : voteCount.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                winner = entry.getKey();
            }
        }
        
        sb.append("\nWINNER: ").append(winner).append(" with ").append(maxVotes).append(" votes!\n\n");

        sb.append("\n=== BLOCKCHAIN INFO ===\n");
        sb.append("Blocks: ").append(blockchain.size()).append("\n");
        sb.append("Latest Block Hash: ").append(blockchain.get(blockchain.size()-1).getHash()).append("\n");
        
        resultsArea.setText(sb.toString());
    }
    
    public static void main(String[] args) {
        BlockchainVotingSystem votingSystem = new BlockchainVotingSystem();
        votingSystem.setVisible(true);
    }
    
    class Block {
        private String hash;
        private String previousHash;
        private String data;
        private long timeStamp;
        private String voterId;
        private String candidate;
        
        public Block(String previousHash, String data) {
            this.previousHash = previousHash;
            this.data = data;
            this.timeStamp = System.currentTimeMillis();
            this.hash = calculateHash();
        }
        
        public String calculateHash() {
            try {
                String input = previousHash + data + timeStamp;
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
                
                StringBuilder hexString = new StringBuilder();
                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public String getHash() { return hash; }
        public String getPreviousHash() { return previousHash; }
        public String getData() { return data; }
        public String getVoterId() { return voterId; }
        public void setVoterId(String voterId) { this.voterId = voterId; }
        public String getCandidate() { return candidate; }
        public void setCandidate(String candidate) { this.candidate = candidate; }
    }
    
    class Voter {
        private String id;
        private String name;
        
        public Voter(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() { return id; }
        public String getName() { return name; }
    }
    
    class Candidate {
        private String name;
        private String party;
        
        public Candidate(String name, String party) {
            this.name = name;
            this.party = party;
        }

        public String getName() { return name; }
        public String getParty() { return party; }
    }
}
