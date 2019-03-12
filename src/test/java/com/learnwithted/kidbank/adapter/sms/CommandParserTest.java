package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import com.learnwithted.kidbank.domain.Account;
import org.junit.Test;

import java.time.Clock;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandParserTest {

  @Test
  public void invalidCommandTextShouldReturnInvalidCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand command = commandParser.parse("mispeld");

    assertThat(command)
        .isInstanceOf(InvalidCommand.class);
  }

  @Test
  public void balanceShouldBeParsedAsBalanceCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand balanceCommand = commandParser.parse("balance");

    assertThat(balanceCommand)
        .isInstanceOf(BalanceCommand.class);
  }

  @Test
  public void depositTextOnlyWithoutAmountShouldBeInvalidCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand command = commandParser.parse("deposit");

    assertThat(command)
        .isInstanceOf(InvalidCommand.class);
  }

  @Test
  public void depositWithInvalidAmountShouldBeInvalidCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand command = commandParser.parse("deposit xyz");

    assertThat(command)
        .isInstanceOf(InvalidCommand.class);
  }

  @Test
  public void depositOnlyWithValidAmountShouldBeDepositCommand() throws Exception {
    Account account = new Account(new FakeTransactionRepository(), Clock.systemDefaultZone());
    CommandParser commandParser = new CommandParser(account);

    TransactionCommand command = commandParser.parse("deposit 15.75");

    DepositCommand expectedDepositCommand = new DepositCommand(account, 15_75);

    assertThat(command)
        .isEqualTo(expectedDepositCommand);
  }

  @Test
  public void emptyTextShouldBeInvalidCommand() throws Exception {
    CommandParser commandParser = new CommandParser(null);

    TransactionCommand command = commandParser.parse("");

    assertThat(command)
        .isInstanceOf(InvalidCommand.class);
  }

  @Test
  public void spendOnlyWithValidAmountShouldBeSpendCommand() throws Exception {
    Account account = new Account(new FakeTransactionRepository(), Clock.systemDefaultZone());
    CommandParser commandParser = new CommandParser(account);

    TransactionCommand command = commandParser.parse("spend 55");

    SpendCommand expectedSpendCommand = new SpendCommand(account, 55_00);

    assertThat(command)
        .isEqualTo(expectedSpendCommand);
  }

}