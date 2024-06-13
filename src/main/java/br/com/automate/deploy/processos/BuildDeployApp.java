package br.com.automate.deploy.processos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BuildDeployApp {
    public static void main(String[] args) {
        // Configura a janela principal
        JFrame frame = new JFrame("Build and Deploy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Painel superior para os checkboxes
        JPanel topPanel = new JPanel();
        JCheckBox buildCheckBox = new JCheckBox("Build");
        buildCheckBox.setSelected(true); // Marca o checkbox "Build" por padrão
        JCheckBox deployCheckBox = new JCheckBox("Deploy");
        deployCheckBox.setEnabled(false); // Desabilita o checkbox "Deploy" inicialmente
        topPanel.add(buildCheckBox);
        topPanel.add(deployCheckBox);

        // Lista de itens clicáveis
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Item 1");
        listModel.addElement("Item 2");
        listModel.addElement("Item 3");
        listModel.addElement("Item 4");
        listModel.addElement("Item 5");

        JList<String> itemList = new JList<>(listModel);
        itemList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Define o renderer personalizado para alternar cores e aumentar a altura dos itens
        itemList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index % 2 == 0) {
                    label.setBackground(new Color(230, 230, 230)); // Cor cinza clara para linhas pares
                } else {
                    label.setBackground(Color.WHITE); // Cor padrão para linhas ímpares
                }
                if (isSelected) {
                    label.setBackground(new Color(173, 216, 230)); // Cor de seleção personalizada
                }
                label.setOpaque(true); // Necessário para exibir o fundo colorido
                label.setPreferredSize(new Dimension(0, 30)); // Aumenta a altura dos itens
                return label;
            }
        });

        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = itemList.locationToIndex(e.getPoint());
                    String selectedItem = itemList.getModel().getElementAt(index);
                    JOptionPane.showMessageDialog(frame, "Item selecionado: " + selectedItem);
                }
            }
        });

        // Adiciona um ListSelectionListener para habilitar o checkbox "Deploy" somente quando os itens 2 e 4 estiverem selecionados
        itemList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                List<String> selectedValues = itemList.getSelectedValuesList();
                boolean item2Selected = selectedValues.contains("Item 2");
                boolean item4Selected = selectedValues.contains("Item 4");
                deployCheckBox.setEnabled(item2Selected || item4Selected);
            }
        });

        JScrollPane listScrollPane = new JScrollPane(itemList);

        // Adiciona os componentes ao frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(listScrollPane, BorderLayout.CENTER);

        // Torna a janela visível
        frame.setVisible(true);
    }
}
