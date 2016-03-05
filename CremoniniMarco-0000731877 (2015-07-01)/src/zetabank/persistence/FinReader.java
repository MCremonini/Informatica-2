package zetabank.persistence;

import java.util.List;

import zetabank.model.TipoFinanziamento;

public interface FinReader {
	public List<TipoFinanziamento> readElements() throws BadFileFormatException;
}
