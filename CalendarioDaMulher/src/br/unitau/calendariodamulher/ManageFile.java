package br.unitau.calendariodamulher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.util.Log;

public class ManageFile {
	private static final String TAG = "ManageFile";
	private Context context;
	private String file;

	public ManageFile(String file, Context context) {
		this.file = file;
		this.context = context;
	}

	/**
	 * Escreve no arquivo texto.
	 * 
	 * @param text
	 *            Texto a ser escrito.
	 * @return True se o texto foi escrito com sucesso.
	 */
	public boolean WriteFile(String text) {
		try {
			// Abre o arquivo para escrita ou cria se n�o existir
			FileOutputStream out = context.openFileOutput(file, Context.MODE_PRIVATE);
			out.write(text.getBytes());
			out.flush();
			out.close();
			return true;

		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return false;
		}
	}

	/**
	 * Faz a leitura do arquivo
	 * 
	 * @return O texto lido.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String ReadFile() throws FileNotFoundException, IOException {
		File file = context.getFilesDir();
		File textfile = new File(file + "/" + this.file);

		FileInputStream input = context.openFileInput(this.file);

		byte[] buffer = new byte[(int) textfile.length()];
		input.read(buffer);
		return new String(buffer);
	}
}