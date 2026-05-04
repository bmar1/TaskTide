package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {

	private JPanel mainPanel = new TaskTideTheme.RoundedCard(36);
	public JButton signUp = new JButton("Don't have an account? Sign up");

	private JLabel usernameLabel = new JLabel("Username:");
	private JTextField usernameField = new JTextField();

	private JLabel passwordLabel = new JLabel("Password:");
	private JPasswordField passwordField = new JPasswordField();
	private JButton revealPassword = new JButton(TaskTideTheme.scaledIcon("reveal.png", 30, 30));

	private JPanel background = new TaskTideTheme.GradientPanel(TaskTideTheme.NAVY, TaskTideTheme.WAVE);
	public JButton signInButton = new JButton("Log in");
	private JLabel title = new JLabel("Log in to continue");
	private JLabel welcome = new JLabel("Welcome Back!");
	private JLabel logo = TaskTideTheme.logoLabel(180);
	private JLabel text = new JLabel("TaskTide");
	private JLabel text2 = new JLabel("Plan your day, follow your priorities,");
	private JLabel text3 = new JLabel("and ride each task to completion.");

	public LoginFrame() {
		setTitle("TaskTide - Login");
		TaskTideTheme.setFrameIcon(this);
		setSize(1280, 760);
		setMinimumSize(new java.awt.Dimension(1100, 700));
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());
		setResizable(true);
		setDefaultCloseOperation(LoginFrame.EXIT_ON_CLOSE);

		frameElementsSetup();
		revalidate();
		repaint();
		setVisible(true);
	}

	private void frameElementsSetup() {
		background.setLayout(new GridBagLayout());
		setContentPane(background);

		JPanel shell = new JPanel(new GridBagLayout());
		shell.setOpaque(false);
		shell.setBorder(BorderFactory.createEmptyBorder(56, 72, 56, 72));
		background.add(shell, fillConstraints(0, 0, 1, 1, 1, 1));

		JPanel brandPanel = new JPanel(new GridBagLayout());
		brandPanel.setOpaque(false);
		brandPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 70));
		shell.add(brandPanel, fillConstraints(0, 0, 1, 1, 0.58, 1));

		text.setFont(TaskTideTheme.DISPLAY_FONT);
		text.setForeground(Color.WHITE);
		text2.setFont(new Font("Sans Serif", Font.BOLD, 28));
		text2.setForeground(Color.WHITE);
		text3.setFont(TaskTideTheme.BODY_FONT);
		text3.setForeground(new Color(224, 247, 252));

		GridBagConstraints brand = new GridBagConstraints();
		brand.gridx = 0;
		brand.gridy = 0;
		brand.anchor = GridBagConstraints.WEST;
		brand.insets = new Insets(0, 0, 20, 0);
		brandPanel.add(logo, brand);

		brand.gridy++;
		brand.insets = new Insets(0, 0, 12, 0);
		brandPanel.add(text, brand);

		brand.gridy++;
		brandPanel.add(text2, brand);

		brand.gridy++;
		brand.insets = new Insets(4, 0, 0, 0);
		brandPanel.add(text3, brand);

		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(new Color(255, 255, 255, 238));
		shell.add(mainPanel, fillConstraints(1, 0, 1, 1, 0.42, 1));

		welcome.setFont(TaskTideTheme.TITLE_FONT);
		welcome.setForeground(TaskTideTheme.TEXT);
		title.setFont(TaskTideTheme.BODY_FONT);
		title.setForeground(TaskTideTheme.MUTED);

		usernameLabel.setFont(TaskTideTheme.BUTTON_FONT);
		usernameLabel.setForeground(TaskTideTheme.TEXT);
		passwordLabel.setFont(TaskTideTheme.BUTTON_FONT);
		passwordLabel.setForeground(TaskTideTheme.TEXT);

		TaskTideTheme.styleTextField(usernameField);
		TaskTideTheme.styleTextField(passwordField);
		TaskTideTheme.styleIconButton(revealPassword);
		TaskTideTheme.stylePrimaryButton(signInButton);
		TaskTideTheme.styleSecondaryButton(signUp);
		signUp.setContentAreaFilled(false);
		signUp.setBorderPainted(false);
		signUp.setToolTipText("Switch between login and sign up");

		addToCard(welcome, 0, 0, 2, new Insets(8, 8, 0, 8));
		addToCard(title, 0, 1, 2, new Insets(0, 8, 32, 8));
		addToCard(usernameLabel, 0, 2, 2, new Insets(0, 8, 8, 8));
		addToCard(usernameField, 0, 3, 2, new Insets(0, 8, 24, 8));
		addToCard(passwordLabel, 0, 4, 2, new Insets(0, 8, 8, 8));
		addToCard(passwordField, 0, 5, 1, new Insets(0, 8, 30, 8));
		addToCard(revealPassword, 1, 5, 1, new Insets(0, 8, 30, 8));
		addToCard(signInButton, 0, 6, 2, new Insets(0, 8, 18, 8));
		addToCard(signUp, 0, 7, 2, new Insets(0, 8, 8, 8));
	}

	private GridBagConstraints fillConstraints(int x, int y, int width, int height, double weightX, double weightY) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		constraints.weightx = weightX;
		constraints.weighty = weightY;
		constraints.fill = GridBagConstraints.BOTH;
		return constraints;
	}

	private void addToCard(JComponent component, int x, int y, int width, Insets insets) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.weightx = x == 0 ? 1 : 0;
		constraints.fill = x == 0 ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = insets;
		mainPanel.add(component, constraints);

	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public JLabel getWelcome() {
		return welcome;
	}

	public void setWelcome(JLabel welcome) {
		this.welcome = welcome;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JButton getSignUp() {
		return signUp;
	}

	public void setSignUp(JButton signUp) {
		this.signUp = signUp;
	}

	public JLabel getUsernameLabel() {
		return usernameLabel;
	}

	public void setUsernameLabel(JLabel usernameLabel) {
		this.usernameLabel = usernameLabel;
	}

	public JTextField getUsernameField() {
		return usernameField;
	}

	public void setUsernameField(JTextField usernameField) {
		this.usernameField = usernameField;
	}

	public JLabel getPasswordLabel() {
		return passwordLabel;
	}

	public void setPasswordLabel(JLabel passwordLabel) {
		this.passwordLabel = passwordLabel;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public void setBackground(JPanel background) {
		this.background = background;
	}

	public JButton getSignInButton() {
		return signInButton;
	}

	public void setSignInButton(JButton signInButton) {
		this.signInButton = signInButton;
	}

	public void setTitle(JLabel title) {
		this.title = title;
	}

	public JLabel getTitleFrame() {
		return title;
	}

	public JButton getRevealPassword() {
		return revealPassword;
	}

	public void setRevealPassword(JButton revealPassword) {
		this.revealPassword = revealPassword;
	}

}