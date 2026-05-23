package com.exemplo.pessoasapp.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.exemplo.pessoasapp.data.entity.Pessoa;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PessoaDao_Impl implements PessoaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Pessoa> __insertionAdapterOfPessoa;

  private final EntityDeletionOrUpdateAdapter<Pessoa> __deletionAdapterOfPessoa;

  private final EntityDeletionOrUpdateAdapter<Pessoa> __updateAdapterOfPessoa;

  private final SharedSQLiteStatement __preparedStmtOfDeletarPorId;

  public PessoaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPessoa = new EntityInsertionAdapter<Pessoa>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pessoas` (`id`,`nome`,`email`,`telefone`,`idade`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Pessoa entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getNome());
        statement.bindString(3, entity.getEmail());
        statement.bindString(4, entity.getTelefone());
        statement.bindLong(5, entity.getIdade());
      }
    };
    this.__deletionAdapterOfPessoa = new EntityDeletionOrUpdateAdapter<Pessoa>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `pessoas` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Pessoa entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPessoa = new EntityDeletionOrUpdateAdapter<Pessoa>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `pessoas` SET `id` = ?,`nome` = ?,`email` = ?,`telefone` = ?,`idade` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Pessoa entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getNome());
        statement.bindString(3, entity.getEmail());
        statement.bindString(4, entity.getTelefone());
        statement.bindLong(5, entity.getIdade());
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfDeletarPorId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pessoas WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object inserir(final Pessoa pessoa, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPessoa.insertAndReturnId(pessoa);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletar(final Pessoa pessoa, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPessoa.handle(pessoa);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object atualizar(final Pessoa pessoa, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPessoa.handle(pessoa);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletarPorId(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletarPorId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletarPorId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Pessoa>> buscarTodas() {
    final String _sql = "SELECT * FROM pessoas ORDER BY nome ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"pessoas"}, false, new Callable<List<Pessoa>>() {
      @Override
      @Nullable
      public List<Pessoa> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfTelefone = CursorUtil.getColumnIndexOrThrow(_cursor, "telefone");
          final int _cursorIndexOfIdade = CursorUtil.getColumnIndexOrThrow(_cursor, "idade");
          final List<Pessoa> _result = new ArrayList<Pessoa>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Pessoa _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpNome;
            _tmpNome = _cursor.getString(_cursorIndexOfNome);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpTelefone;
            _tmpTelefone = _cursor.getString(_cursorIndexOfTelefone);
            final int _tmpIdade;
            _tmpIdade = _cursor.getInt(_cursorIndexOfIdade);
            _item = new Pessoa(_tmpId,_tmpNome,_tmpEmail,_tmpTelefone,_tmpIdade);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Pessoa>> buscarPorNomeOuEmail(final String busca) {
    final String _sql = "SELECT * FROM pessoas WHERE nome LIKE '%' || ? || '%' OR email LIKE '%' || ? || '%' ORDER BY nome ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, busca);
    _argIndex = 2;
    _statement.bindString(_argIndex, busca);
    return __db.getInvalidationTracker().createLiveData(new String[] {"pessoas"}, false, new Callable<List<Pessoa>>() {
      @Override
      @Nullable
      public List<Pessoa> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfTelefone = CursorUtil.getColumnIndexOrThrow(_cursor, "telefone");
          final int _cursorIndexOfIdade = CursorUtil.getColumnIndexOrThrow(_cursor, "idade");
          final List<Pessoa> _result = new ArrayList<Pessoa>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Pessoa _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpNome;
            _tmpNome = _cursor.getString(_cursorIndexOfNome);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpTelefone;
            _tmpTelefone = _cursor.getString(_cursorIndexOfTelefone);
            final int _tmpIdade;
            _tmpIdade = _cursor.getInt(_cursorIndexOfIdade);
            _item = new Pessoa(_tmpId,_tmpNome,_tmpEmail,_tmpTelefone,_tmpIdade);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object buscarPorId(final long id, final Continuation<? super Pessoa> $completion) {
    final String _sql = "SELECT * FROM pessoas WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Pessoa>() {
      @Override
      @Nullable
      public Pessoa call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfTelefone = CursorUtil.getColumnIndexOrThrow(_cursor, "telefone");
          final int _cursorIndexOfIdade = CursorUtil.getColumnIndexOrThrow(_cursor, "idade");
          final Pessoa _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpNome;
            _tmpNome = _cursor.getString(_cursorIndexOfNome);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpTelefone;
            _tmpTelefone = _cursor.getString(_cursorIndexOfTelefone);
            final int _tmpIdade;
            _tmpIdade = _cursor.getInt(_cursorIndexOfIdade);
            _result = new Pessoa(_tmpId,_tmpNome,_tmpEmail,_tmpTelefone,_tmpIdade);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
