package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import model.Usuario;

import model.Tipo;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class FrmListado extends JFrame {
	
	static EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
	static EntityManager em = fabrica.createEntityManager();

	private JPanel contentPane;
	private JTextArea txtSalida;
	private JComboBox cboUsuario;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmListado frame = new FrmListado();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmListado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		btnListar.setBounds(324, 29, 89, 23);
		contentPane.add(btnListar);
		
		JLabel lblNewLabel = new JLabel("Usuario :");
		lblNewLabel.setBounds(10, 33, 102, 14);
		contentPane.add(lblNewLabel);
		
		txtSalida = new JTextArea();
		txtSalida.setBounds(20, 68, 378, 159);
		contentPane.add(txtSalida);
		
		cboUsuario = new JComboBox();
		cboUsuario.setBounds(78, 30, 151, 20);
		contentPane.add(cboUsuario);
		llenaCombo();
		
	}
void llenaCombo() {
		
		//LLenado de combo de categorias
		//obtener un listado de las categorias
		List<Usuario> lstCate = em.createQuery("select u from Usuario u", Usuario.class).getResultList();
		//pasar el listado al combo
		
		cboUsuario.addItem("Seleccione...");
		for (Usuario c : lstCate) {
			cboUsuario.addItem(c.getNom_usua());
		}
		
	}
	
	void registrar() {
		
		txtSalida.setText("");
		int usuario = cboUsuario.getSelectedIndex();
		
		try {
			Usuario u = em.createQuery("select u from Usuario u where u.cod_usua = :xusua",Usuario.class).
					setParameter("xusua",usuario).
					getSingleResult();
			
			System.out.println(u);
			imprimir("Codigo...." + u.getCod_usua());
			imprimir("Nombre ...." + u.getNom_usua());
			imprimir("Correo...." + u.getCorreo());
			imprimir(" Clave :" + u.getClave());
			imprimir("Fecha...." + u.getFna_usua());
			imprimir("-----------------------------------");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"Error   :" + e);
		}
		
		
	}
	
	void imprimir(String s) {
		txtSalida.append(s + "\n");
	}
}
